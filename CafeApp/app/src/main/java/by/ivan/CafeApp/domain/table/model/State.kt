package by.ivan.CafeApp.domain.table.model

enum class State(val i: Int) {
    Occupied(i = 0),
    NotOccupied(i = 1),
    Reserved(i = 2)
}
//TODO
fun Int.toState(): State {
    return when (this) {
        0 -> {
            State.Occupied
        }

        1 -> {
            State.NotOccupied
        }

        2 -> {
            State.Reserved
        }

        else -> {
            State.NotOccupied
        }
    }
}