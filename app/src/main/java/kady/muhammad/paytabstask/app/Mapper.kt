package kady.muhammad.paytabstask.app

interface Mapper<in I, out O> {
    fun map(input: I): O
}