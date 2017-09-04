data class Cell(val x: Int, val y: Int)

data class Board(val cells: HashSet<Cell> = HashSet<Cell>()) {
    fun isAlive(cell: Cell) = cells.contains(cell)
    fun setAlive(cell: Cell) = apply { cells.add(cell) }
    fun isEmpty() = cells.isEmpty()

    fun calculateNextGeneration() = let {
        val nextGeneration = cells.filter { shouldBeAliveInNextGeneration(it) } +
                reproductionCandidates().filter { shouldBeRebornInNextGeneration(it) }
        Board(nextGeneration.toHashSet())
    }

    fun reproductionCandidates(): Set<Cell> = cells.flatMap { getAllEmptyNeighbors(it) }.toHashSet()

    fun shouldBeAliveInNextGeneration(cell: Cell) = shouldBeAliveInNextGeneration(countLivingNeighbors(cell))
    fun shouldBeAliveInNextGeneration(nrNeighbors: Int) = nrNeighbors == 2 || nrNeighbors == 3

    fun shouldBeRebornInNextGeneration(cell: Cell) = shouldBeRebornInNextGeneration(countLivingNeighbors(cell))
    fun shouldBeRebornInNextGeneration(nrNeighbors: Int) = nrNeighbors == 3

    fun countLivingNeighbors(cell: Cell): Int = getAllNeighbors(cell).filter { isAlive(it) }.count()

    fun getAllEmptyNeighbors(cell: Cell): Set<Cell> = getAllNeighbors(cell).minus(cells)

    fun getAllNeighbors(cell: Cell): Set<Cell> = HashSet<Cell>().apply {
        for (dx in -1..1)
            for (dy in -1..1)
                if (dx != 0 || dy != 0)
                    add(Cell(cell.x + dx, cell.y + dy))
    }
}

