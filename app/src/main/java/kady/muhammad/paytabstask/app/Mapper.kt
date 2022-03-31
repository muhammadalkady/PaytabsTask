package kady.muhammad.paytabstask.app

interface Mapper<in I, out O> {
    fun map(input: I): O
}

interface ListMapper<in I, out O> : Mapper<List<I>, List<O>>