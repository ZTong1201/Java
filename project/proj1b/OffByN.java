public class OffByN implements CharacterComparator {

    private int thres;

    public OffByN(int N){
        thres = N;
    }

    @Override
    public boolean equalChars(char a, char b) {
        int diff = a - b;
        if (diff == thres || diff == (-1)*thres) {
            return true;
        }
        return false;
    }
}
