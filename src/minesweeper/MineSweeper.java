package minesweeper;

import java.util.Random;

public class MineSweeper {

    private final Board board;
    private final Random random;
    private final int rows;
    private final int cols;
    private int mineCount;
    //holds how many mines the user has successfully marked
    private int minesMarked;

    public MineSweeper(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.board = new Board(rows, cols);
//        this.board = new Board();
        this.random = new Random();
    }

    /**
     *
     * @param mineCount  randomly sets mines based on param passed, will not set a mine on passed row and col
     */
    public void populateMines(int mineCount, int row, int col, String choice) {
        this.mineCount = mineCount;
        int idx = 0;

        //prevent overflow if too many mines given
        if (mineCount > board.rows() * board.cols()) {
            mineCount = board.rows()  * board.cols() / 2;
        }


        if ("mine".equals(choice)) {
            board.updateCell(State.MINE, row, col);
            idx++;
        }


        start:while (idx < mineCount) {
            int randomI;
            int randomJ;

            if (idx == 0) {
                randomI = row;
            } else {
                randomI = random.nextInt(board.rows());
            }

            randomJ = random.nextInt(board.cols());

            //don't put a mine around free space
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (i + randomI == row && j + randomJ == col) {
                        continue start;
                    }
                }
            }


            if (board.isCellOpen(randomI, randomJ)) {
                board.updateCell(State.MINE, randomI, randomJ);
                idx++;
            }
        }
    }



    /**
     * loops through every cell and sets mine count around cells
     */
    public void populateMineCounts(int row, int col) {
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                int count = getMineCount(i, j);

                //ignore the position where the user wants free
                if (i == row && j == col) {
                    continue;
                }

                if (count != 0) {
                    board.getCell(i, j).setState(State.get("" + count));
                }
            }
        }
    }

    /**
     *
     * @param x  row position
     * @param y  col position
     * @return   the amount of mines around a cell
     */
    public int getMineCount(int x, int y) {
        Cell startingCell = board.getCell(x, y);

        if (startingCell.getState() == State.MINE || !startingCell.isOpen()) {
            return 0;
        }

        int mineCount = 0;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Cell current = board.getCell(x + i, y + j);

                if (current == null) {
                    continue;
                }

                if (current.isMine()) {
                    mineCount++;
                }
            }

        }

        return mineCount;
    }


    public void explore(int row, int col) {
        if (board.isCellNumber(row, col) || board.isCellMine(row, col)) {
            return;
        }

        if (board.isCellOpen(row, col)) {

            board.getCell(row, col).setState(State.FREE);
            board.getCell(row, col).setVisible(true);

            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {

                    Cell cell = board.getCell(row + i, col + j);

                    if (cell != null && cell.isOpen()) {
                        explore(row + i, col + j);
                    } else if (cell != null && cell.isNumber()) {
                         cell.setVisible(true);
                    } else if (cell != null && cell.isMarked()) {
                        board.unsetCell(row + i, col + j);
                        cell.setVisible(true);
                    }
                }
            }

        }
    }



    public void exploreAroundMine() {
        explore(0, 0);
    }

    public void printBoard() {
        this.board.print();
    }

    public void showCell(int row, int col) {
        Cell cell = board.getCell(row, col);

        if (cell.isOpen()) {
            cell.setState(State.FREE);
        }

        cell.setVisible(true);
    }

    public boolean isCellMarked(int row, int col) {
        return board.isCellMarked(row, col);
    }

    public boolean isCellMine(int row, int col) {
        return board.isCellMine(row, col);
    }


    public boolean isCellOpen(int row, int col) {
        return this.board.isCellOpen(row, col);
    }

    public void setCell(State state, int row, int col) {
        if (this.board.isCellMine(row, col)) {
            minesMarked++;
        }

        this.board.getCell(row, col).setState(state);
        this.board.getCell(row, col).setVisible(true);
    }

    public void unsetCell(int row, int col) {
        if (this.board.isCellMine(row, col)) {
            minesMarked--;
        }

        this.board.unsetCell(row, col);
        this.board.getCell(row, col).setVisible(false);

    }

    public boolean isGameOver() {
        if (board.getMarkerCount() != mineCount) {
            return false;
        }

        return minesMarked == mineCount;
    }

    public void setMinesVisible() {
       this.board.showMinesMarked();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

}
