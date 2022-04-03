package kady.muhammad.paytabstask.app

interface IMapper<in I, out O> {
    fun map(input: I): O
}
