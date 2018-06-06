public class HorseRace {
    private Horse[] myHorses;
    private static int courseLength = 100;

    public HorseRace(Horse[] newHorses) {
        myHorses = newHorses;
    }

    public void readyForRace() {
        // Make sure we have enough horses for a race
        if (myHorses.length < 3)
            throw new IllegalArgumentException("At least three horses are required for a race.");

        for (int i = 0; i < myHorses.length; i++) {

            Horse h = myHorses[i];

            // Move each horse, type can change specifics
            switch (h.type) {
                case 1:
                    // Quarterhorse
                    if (h.position <= (courseLength / 4)) {
                        // Faster in first 1/4th of race
                        h.position = h.position + (h.speed * 2);
                    } else {
                        // Last 2/3rds of race, normal speed
                        h.position = h.position + h.speed;
                    }
                    break;

                case 2:
                    // Filly
                    if (h.position >= (courseLength / 2)) {
                        // Faster in second half of race
                        h.position = h.position + (h.speed * 2);
                    } else {
                        // First half, normal speed
                        h.position = h.position + h.speed;
                    }
                    break;

                default:
                    // All other horses
                    h.position = h.position + h.speed;
            }

        }

        for (int i = 0; i < myHorses.length; i++) {
            System.out.println(myHorses[i].position);
        }
    }

    public static class Horse {
        public String name;
        public int position = 0;
        public int type;
        public int speed;

        public Horse(String aName, int aType, int aSpeed) {
            name = aName;
            type = aType;
            speed = aSpeed;
        }
    }

    public static void main(String[] args) {
        Horse[] horses = new Horse[]{new Horse("Man o' War", 1, 10),
                new Horse("Secretariat", 2, 12),
                new Horse("FutureSoap", 3, 10)};
        HorseRace hr = new HorseRace(horses);
        hr.readyForRace();
    }
}