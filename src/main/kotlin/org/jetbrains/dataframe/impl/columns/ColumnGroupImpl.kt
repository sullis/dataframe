package org.jetbrains.dataframe.impl.columns

import org.jetbrains.dataframe.*
import org.jetbrains.dataframe.columns.ColumnWithPath
import org.jetbrains.dataframe.columns.DataColumn
import org.jetbrains.dataframe.columns.ColumnGroup
import org.jetbrains.dataframe.createType
import org.jetbrains.dataframe.impl.renderSchema
import java.lang.UnsupportedOperationException
import kotlin.reflect.KType

internal val anyRowType = createType<AnyRow>()

internal class ColumnGroupImpl<T>(override val df: DataFrame<T>, val name: String) : ColumnGroup<T>, DataColumnInternal<DataRow<T>>,
    DataColumnGroup<T>, DataFrame<T> by df {

    override fun values() = df.rows()

    override fun ndistinct() = distinct.nrow()

    override fun type() = anyRowType

    private val distinct by lazy { df.distinct() }

    private val set by lazy { distinct.rows().toSet() }

    override fun toSet() = set

    override fun size() = df.nrow()

    override fun get(index: Int) = df[index]

    override fun get(firstIndex: Int, vararg otherIndices: Int): ColumnGroup<T> = DataColumn.create(name, df.get(firstIndex, *otherIndices))

    override fun slice(range: IntRange) = ColumnGroupImpl(df[range], name)

    override fun rename(newName: String) = ColumnGroupImpl(df, newName)

    override fun defaultValue() = null

    override fun slice(indices: Iterable<Int>) = ColumnGroupImpl(df[indices], name)

    override fun slice(mask: BooleanArray) = ColumnGroupImpl(df.getRows(mask), name)

    override fun addParent(parent: ColumnGroup<*>): DataColumn<DataRow<T>> = ColumnGroupWithParent(parent, this)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val g = other as? ColumnGroup<*> ?: return false
        return name == g.name() && df == other.df
    }

    override fun hashCode(): Int {
        return name.hashCode() * 31 + df.hashCode()
    }

    override fun tryGetColumn(columnName: String) = df.tryGetColumn(columnName)

    override fun toString() = "$name: {${renderSchema(df)}}"

    override fun changeType(type: KType) = throw UnsupportedOperationException()

    override fun name() = name

    override fun distinct() = ColumnGroupImpl(distinct, name)

    override fun resolveSingle(context: ColumnResolutionContext): ColumnWithPath<DataRow<T>>? {
        return df.resolveSingle(context)
    }
}