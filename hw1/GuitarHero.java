import es.datastructur.synthesizer.GuitarString;

public class GuitarHero {

    public static void main(String[] args) {
        GuitarString[] pianoString = new GuitarString[37];
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (keyboard.indexOf(key)>=0){
                    int index = keyboard.indexOf(key);
                    pianoString[index] = new GuitarString(440.0 * Math.pow(2, (double) index / 12.0));
                    pianoString[index].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for (int i = 0; i < pianoString.length; i++) {
                if (pianoString[i] != null) {
                    sample += pianoString[i].sample();
                }
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < pianoString.length; i++) {
                if (pianoString[i] != null) {
                    pianoString[i].tic();
                }
            }
        }
    }
}
