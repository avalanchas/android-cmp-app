package com.sourcepoint.gdpr_cmplibrary.data

import com.sourcepoint.gdpr_cmplibrary.assertEquals
import com.sourcepoint.gdpr_cmplibrary.data.Either.Right
import org.junit.Test
import java.lang.RuntimeException

class EitherTest{

    @Test
    fun `First law - left identity`(){

        val value = 1

        fun f(a : Int): Either<Int> = Right(a * 2)

        val original = Right(value)
        val new = original.flatMap { f(it) }

        new.assertEquals(f(1))
    }

    @Test
    fun `Second law - right identity`(){

        val original = Right(1)
        val new = original.flatMap { Right(it) }

        new.assertEquals(original)
    }

    @Test
    fun `Third law - Associativity`(){

        val original = Right(1)

        fun f(a : Int): Right<Int> = Right(a * 2)
        fun g(a : Int): Right<Int> = Right(a + 6)

        val first = original.flatMap { f(it) }.flatMap { g(it) }
        val second = original.flatMap { f(it).flatMap { it2 -> g(it2) } }

        second.assertEquals(first)
    }

    @Test
    fun `GIVEN a Left CHECK if it was consumed`(){
        val sut = Either.Left(RuntimeException())
        sut.isConsumed.assertEquals(false)
        sut.isConsumed = true
        sut.isConsumed.assertEquals(true)
    }
}