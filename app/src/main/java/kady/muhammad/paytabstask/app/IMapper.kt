package kady.muhammad.paytabstask.app

fun interface IMapper<in I, out O> {
    fun map(input: I): O
}
