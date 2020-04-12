class Statistic <T> {
    public T maximum;
    public T minimum;

    @SafeVarargs
    public final T getMaxKPI(T... varags) { // <T> list named VARiableARGuments -> LIST
        maximum = varags[0];
        if(maximum instanceof User) {
            for (T varag : varags) {
                if (((User) maximum).getKPI() < ((User) varag).getKPI()) {
                    maximum = varag;
                }
            }
        }
        return maximum;
    }

    @SafeVarargs
    public final T getMinKPI(T... varags) { // <T> list named VARiableARGuments -> LIST
        minimum = varags[0];
        if(minimum instanceof User) {
            for (T varag : varags) {
                if (((User) minimum).getKPI() > ((User) varag).getKPI()) {
                    minimum = varag;
                }
            }
        }
        return minimum;
    }

    @SafeVarargs
    public final T getAvgKPI(T... varags) { // <T> list named VARiableARGuments -> LIST
        Integer avg = 0;
        Integer distance;
        Integer nearestDistance;
        Integer nearestIndex = 0;
        if(varags[0] instanceof User) {
            for (T varag : varags)
                avg += ((User) varag).getKPI();

            avg = avg / varags.length;
             nearestDistance = Math.abs(((User)varags[0]).getKPI() - avg);
            nearestIndex = 0;
            for(int i = 1; i < varags.length; i++) {
                T varag = varags[i];
                distance = Math.abs(((User)varag).getKPI() - avg);
                if (distance < nearestDistance) {
                    nearestIndex = i;
                    nearestDistance = distance;
                }
            }
        }
        return varags[nearestIndex];
    }
}