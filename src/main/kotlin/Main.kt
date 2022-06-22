import java.lang.IndexOutOfBoundsException
import java.text.DecimalFormat

fun main() {
    println("Enter the number of rows:")
    val rows = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val columns = readLine()!!.toInt()

    val cinema = MutableList<MutableList<Char>>(rows) { MutableList<Char>(columns) { 'S' } }

    while (true) {
        println("1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit\n")
        when (readLine()!!.toInt()) {
            1 -> showCinema(cinema, columns)
            2 -> buyTicket(cinema, rows, columns)
            3 -> showStatistics(cinema, rows, columns)
            0 -> break
            else -> println("Incorrect input.")
        }
    }
}


fun showCinema(cinema: List<List<Char>>, columns: Int) {
    print("\nCinema:\n ")
    for (i in 1..columns) print(" $i")
    for (i in cinema.indices) {
        print("\n${i + 1} ${cinema[i].joinToString(" ")}")
    }
    println("\n")
}

fun buyTicket(cinema: MutableList<MutableList<Char>>, rows: Int, columns: Int) {
    while (!isCinemaFull(cinema)){
        try {
            println("\nEnter a row number:")
            val row = readLine()!!.toInt()
            println("Enter a seat number in that row:")
            val column = readLine()!!.toInt()
            if (cinema[row - 1][column - 1] == 'B') {
                println("That ticket has already been purchased!")
            } else {
                val price = if (rows * columns <= 60 || row <= rows / 2) 10 else 8
                println("\nTicket price: $$price\n")
                cinema[row - 1][column - 1] = 'B'
                break
            }
        } catch (e: IndexOutOfBoundsException) {
            println("Wrong input!")
        }
    }
}

fun isCinemaFull(cinema: MutableList<MutableList<Char>>): Boolean {
    val rows = cinema.size
    val columns = cinema[0].size
    val fullCinema = List<List<Char>>(rows) { List<Char>(columns) { 'B' } }
    return if (cinema == fullCinema) {
        println("The cinema is full now")
        true
    } else false
}

fun showStatistics(cinema: List<List<Char>>, rows: Int, columns: Int) {
    var purchasedTickets = 0
    val purchasedPercentage: String
    var income = 0
    val totalIncome = if (rows * columns > 60) {
        rows / 2 * columns * 10 + (rows - rows / 2) * columns * 8
    } else rows * columns * 10
    for (i in cinema.indices) {
        if (rows * columns > 60 && (i + 1) > rows / 2) {
            cinema[i].forEach { if (it == 'B') { purchasedTickets++; income += 8 } }
        } else {
            cinema[i].forEach { if (it == 'B') { purchasedTickets++; income += 10 } }
        }
    }
    val purchasedPercentageNotFormatted = purchasedTickets.toDouble() * 100 / (rows * columns)
    purchasedPercentage = DecimalFormat("#0.00").format(purchasedPercentageNotFormatted)

    println("Number of purchased tickets: $purchasedTickets\n" +
            "Percentage: $purchasedPercentage%\n" +
            "Current income: $$income\n" +
            "Total income: $$totalIncome\n")
}