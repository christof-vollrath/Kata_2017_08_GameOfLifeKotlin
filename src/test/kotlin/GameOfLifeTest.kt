import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldEqual
import io.kotlintest.specs.StringSpec

class GameOfLifeSpec : StringSpec() { init {
    "A new board should have only dead fields" {
        val board = Board()
        board.isEmpty() shouldBe true
    }

    "Living fields in a board can be added and should be alive" {
        val board = Board()
        board.setAlive(Cell(1, 1))
        board.isAlive(Cell(1, 1)) shouldBe true
    }

    "An empty board should become an empty board in the next generation" {
        val board = Board()
        val nextGenerationBoard = board.calculateNextGeneration()
        nextGenerationBoard.isEmpty() shouldBe true
    }

    "A lonely cell on the board should die and the board should become empty" {
        val board = Board().setAlive(Cell(3, 3))
        val nextGenerationBoard = board.calculateNextGeneration()
        nextGenerationBoard.isEmpty() shouldBe true
    }

    "A block on the board should survive" {
        val board = Board()
                .setAlive(Cell(0, 0))
                .setAlive(Cell(0, 1))
                .setAlive(Cell(1, 0))
                .setAlive(Cell(1, 1))
        val nextGenerationBoard = board.calculateNextGeneration()
        nextGenerationBoard shouldEqual board
    }

    "A blinker should blink" {
        val board = Board()
                .setAlive(Cell(0, 1))
                .setAlive(Cell(1, 1))
                .setAlive(Cell(2, 1))
        val nextGenerationBoard = board.calculateNextGeneration()
        nextGenerationBoard.isEmpty() shouldBe false
        val blinked = Board()
                .setAlive(Cell(1, 0))
                .setAlive(Cell(1, 1))
                .setAlive(Cell(1, 2))
        nextGenerationBoard shouldEqual blinked
    }

    // Internal
    "A cell without living neighbors should have neighbor count 0" {
        val board = Board()
        board.countLivingNeighbors(Cell(3, 3)) shouldBe 0
    }

    "An empty cell with one right neighbor should have neighbor count 1" {
        val board = Board().setAlive(Cell(4, 3))
        board.countLivingNeighbors(Cell(3, 3)) shouldBe 1
    }

    "A living cell with one right neighbor should have neighbor count 1" {
        val board = Board().setAlive(Cell(3, 3)).setAlive(Cell(4, 3))
        board.countLivingNeighbors(Cell(3, 3)) shouldBe 1
    }

    "An empty cell with one upper neighbor should have neighbor count 1" {
        val board = Board().setAlive(Cell(3, 4))
        board.countLivingNeighbors(Cell(3, 3)) shouldBe 1
    }

    "A living cell without one upper neighbor should have neighbor count 1" {
        val board = Board().setAlive(Cell(3, 3)).setAlive(Cell(3, 4))
        board.countLivingNeighbors(Cell(3, 3)) shouldBe 1
    }

    "A cell has 8 candidates for reproduction arround it" {
        val board = Board().setAlive(Cell(0, 0))
        board.reproductionCandidates().size shouldBe 8
    }

    "Two cell have 10 candidates for reproduction arround it" {
        val board = Board().setAlive(Cell(0, 0)).setAlive(Cell(1, 0))
        board.reproductionCandidates().size shouldBe 10
    }

}}
