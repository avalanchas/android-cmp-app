package com.sourcepoint.gdpr_cmplibrary.data

/**
 * Either pattern implementation
 */
internal sealed class Either<out R> {
    /**
     * @param r represents the content
     */
    data class Right<R>(val r: R) : Either<R>()
    /**
     * @param t represents the exception
     * @param isConsumed is used to know if the [Throwable] has been logged already
     */
    data class Left(val t: Throwable, var isConsumed : Boolean = false) : Either<Nothing>()
}

/**
 * This extension is an implementation of the `flatMap` pattern
 *
 * @param f closure to execute which receive in input the content
 * of the either obj and has to give an Either object as a computational result
 *
 * @return an either object containing the transformation
 */
internal inline fun <B, C> Either<B>.flatMap(f: (B) -> Either<C>): Either<C> =
    this.let {
        when (it) {
            is Either.Right -> f(it.r)
            is Either.Left -> it
        }
    }

/**
 * This extension is an implementation of the `map` pattern
 *
 * @param f closure to execute which receive in input the content
 * of the either obj and has to give generic object as a computational result
 *
 * @return an either object containing the transformation
 */
internal inline fun <B, C> Either<B>.map(f: (B) -> C): Either<C> =
    flatMap { Either.Right(f(it)) }
