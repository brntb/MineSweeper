package minesweeper;

public class Board {

    private final Cell[][] board;

    public Board(int rows, int cols) {
        this.board = new Cell[rows][cols];

        //init board so each cell has open state
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.board[i][j] = new Cell();
            }
        }
    }

    public int rows() {
        return board.length;
    }

    public int cols() {
        return board[0].length;
    }

    public void updateCell(State state, int row, int col) {
        this.board[row][col].setState(state);
    }

    //returns a cell to previous state
    public void unsetCell(int row, int col) {
        Cell cell = board[row][col];
        cell.setState(cell.getPreviousState());
    }

    public boolean isCellOpen(int row, int col) {
        return board[row][col].isOpen();
    }

    public boolean isCellMine(int row, int col) {
        return board[row][col].getState() == State.MINE;
    }

    public boolean isCellNumber(int row, int col) {
        return board[row][col].isNumber();
    }

    public boolean isCellMarked(int row, int col) {
        return board[row][col].isMarked();
    }

    public Cell getCell(int x, int y) {
        //if invalid coordinates given, return null
        if (x < 0 || x >= cols()) {
            return null;
        }

        if (y < 0 || y >= rows()) {
            return null;
        }

        return board[x][y];
    }

    public int getMarkerCount() {
        int count = 0;

        for (Cell[] row : board) {
            for (Cell cell : row) {
                if (cell.isMarked()) {
                    count++;
                }
            }
        }

        return count;
    }

    public void showMinesMarked() {
        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < cols(); j++) {
                Cell current = getCell(i, j);
                if (current.isMine()) {
                    current.setVisible(true);
                }
            }
        }
    }


    public void print() {
        int currentRow = 1;

        System.out.println("\n |123456789|\n" +
                "-|---------|");
        for (Cell[] cells : board) {
            System.out.print(currentRow++ + "|");
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(cells[j]);
            }
            System.out.print("|\n");
        }
        System.out.println("-|---------|");
    }


}
