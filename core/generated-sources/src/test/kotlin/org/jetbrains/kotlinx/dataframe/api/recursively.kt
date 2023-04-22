package org.jetbrains.kotlinx.dataframe.api

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.jetbrains.kotlinx.dataframe.alsoDebug
import org.jetbrains.kotlinx.dataframe.columns.ColumnWithPath
import org.jetbrains.kotlinx.dataframe.impl.columns.singleImpl
import org.jetbrains.kotlinx.dataframe.impl.columns.transform
import org.jetbrains.kotlinx.dataframe.samples.api.TestBase
import org.jetbrains.kotlinx.dataframe.samples.api.city
import org.jetbrains.kotlinx.dataframe.samples.api.firstName
import org.jetbrains.kotlinx.dataframe.samples.api.name
import org.junit.Test

class Recursively : TestBase() {

    fun List<ColumnWithPath<*>>.print() {
        forEach {
            if (it.isValueColumn()) println("${it.name}: ${it.type()}")
            else it.print()
        }
        println()
    }

    infix fun List<ColumnWithPath<*>>.shouldBe(other: List<ColumnWithPath<*>>) {
        this.map { it.name to it.path } shouldBe other.map { it.name to it.path }
    }

    infix fun List<ColumnWithPath<*>>.shouldNotBe(other: List<ColumnWithPath<*>>) {
        this.map { it.name to it.path } shouldNotBe other.map { it.name to it.path }
    }

    private val recursivelyGoal = dfGroup.getColumnsWithPaths { dfs { true } }
        .sortedBy { it.name }

    private val recursivelyNoGroups = dfGroup.getColumnsWithPaths { allDfs(false) }
        .sortedBy { it.name }

    private val recursivelyString = dfGroup.getColumnsWithPaths { dfsOf<String?>() }
        .sortedBy { it.name }

    @Test
    fun `first, last, and single`() {
        listOf(
            dfGroup.select { name.firstName.firstName },

            dfGroup.select { first { it.data.any { it == "Alice" } }.recursively() },
            dfGroup.select { last { it.data.any { it == "Alice" } }.recursively() },
            dfGroup.select { single { it.data.any { it == "Alice" } }.recursively() },
        ).shouldAllBeEqual()

        listOf(
            dfGroup.select { city },

            dfGroup.select { first { it.data.any { it == "London" } }.recursively() },
            dfGroup.select { last { it.data.any { it == "London" } }.recursively() },
            dfGroup.select { single { it.data.any { it == "London" } }.recursively() },
        ).shouldAllBeEqual()
    }

//    @Test
//    fun `get at`() {
//        dfGroup.getColumnsWithPaths { it[0].recursively() }.print()
//    }

//    @Test
//    fun `combination`() {
//        dfGroup.getColumnsWithPaths {
//            cols { it.name in listOf("name", "firstName") }
//                .last().recursively()
//        } shouldNotBe
//            dfGroup.getColumnsWithPaths {
//                cols { it.name in listOf("name", "firstName") }.recursively()
//                    .last().recursively()
//            }
//    }

//    @Test
//    fun `recursively`() {
//        dfGroup.getColumnsWithPaths { recursively() }.sortedBy { it.name } shouldBe recursivelyGoal
//        dfGroup.getColumnsWithPaths { rec(includeGroups = false) }.sortedBy { it.name } shouldBe recursivelyNoGroups
//    }

    @Test
    fun `all recursively`() {
        dfGroup.getColumnsWithPaths { all().recursively() }.sortedBy { it.name } shouldBe recursivelyGoal
        dfGroup.getColumnsWithPaths { all().rec(includeGroups = false) }
            .sortedBy { it.name } shouldBe recursivelyNoGroups
    }

    @Test
    fun `cols recursively`() {
        dfGroup.getColumnsWithPaths { cols().recursively() }.sortedBy { it.name } shouldBe recursivelyGoal
        dfGroup.getColumnsWithPaths { cols().rec(includeGroups = false) }
            .sortedBy { it.name } shouldBe recursivelyNoGroups
    }

    @Test
    fun `colsOf recursively`() {
        dfGroup.getColumnsWithPaths { colsOf<String?>().recursively() }.sortedBy { it.name } shouldBe recursivelyString
        dfGroup.getColumnsWithPaths { colsOf<String?>().rec(includeGroups = false) }
            .sortedBy { it.name } shouldBe recursivelyString
    }

    @Test
    fun `all allRecursively`() {
        dfGroup.getColumnsWithPaths { all().all().recursively() }.sortedBy { it.name } shouldBe recursivelyGoal
        dfGroup.getColumnsWithPaths { all().all().rec(includeGroups = false) }
            .sortedBy { it.name } shouldBe recursivelyNoGroups
    }

    @Test
    fun `cols allRecursively`() {
        dfGroup.getColumnsWithPaths { cols().all().recursively() }.sortedBy { it.name } shouldBe recursivelyGoal
        dfGroup.getColumnsWithPaths { cols().all().rec(includeGroups = false) }
            .sortedBy { it.name } shouldBe recursivelyNoGroups
    }

//    @Test
//    fun `accessor recursively`() {
//        listOf(
//            dfGroup.getColumnsWithPaths { name.recursively() }.sortedBy { it.name }.map { it.name to it.path },
//            dfGroup.getColumnsWithPaths { it["name"].recursively() }.sortedBy { it.name }.map { it.name to it.path },
//            dfGroup.getColumnsWithPaths { name.dfs { true } }.sortedBy { it.name }.map { it.name to it.path },
//        ).shouldAllBeEqual()
//    }
}
