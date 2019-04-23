public class Position {
    private int x_cor;
    private int y_cor;

    public Position(int x_cor, int y_cor) {

        this.x_cor = x_cor;
        this.y_cor = y_cor;
    }

    public int getX() {
        return x_cor;
    }

    public int getY() {
        return y_cor;
    }

    @Override
    public String toString() {
        return "X: "+x_cor+" Y: "+y_cor;
    }
}
