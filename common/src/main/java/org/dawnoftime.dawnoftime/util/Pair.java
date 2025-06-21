/**
 *
 */
package org.dawnoftime.dawnoftime.util;

import java.util.Objects;

/**
 * @author seyro
 */
public class Pair<S1, S2> {
    private S1 s1;
    private S2 s2;

    public Pair() {

    }

    public Pair(final S1 s1In) {
        this.s1 = s1In;
    }

    public Pair(final S1 s1In, final S2 s2In) {
        this.s1 = s1In;
        this.s2 = s2In;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.s1, this.s2);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked") final Pair<S1, S2> other = (Pair<S1, S2>) obj;
        return Objects.equals(this.s1, other.s1) && Objects.equals(this.s2, other.s2);
    }

    /**
     * @return the s1
     */
    public S1 getS1() {
        return this.s1;
    }

    /**
     * @param s1In the s1 to set
     */
    public void setS1(final S1 s1In) {
        this.s1 = s1In;
    }

    /**
     * @return the s2
     */
    public S2 getS2() {
        return this.s2;
    }

    /**
     * @param s2In the s2 to set
     */
    public void setS2(final S2 s2In) {
        this.s2 = s2In;
    }
}
