package minesweeper;

public class Cell {

    private State state;
    private State previousState;
    private boolean isVisible = false;

    public Cell() {
        this.state = State.OPEN;
    }

    public void setState(State state) {
        this.previousState = this.state;
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public State getPreviousState() {
        return this.previousState;
    }

    public boolean isOpen() {
        return this.state == State.OPEN;
    }

    public boolean isMine() {
        return this.state == State.MINE;
    }

    public boolean isMarked() {
        return this.state == State.MARKER;
    }

    public boolean isNumber() {
        return !isOpen() && !isMine() && this.state != State.MARKER;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public String toString() {
        return  !isVisible ? State.OPEN.getState() : this.state.getState();
    }

}
