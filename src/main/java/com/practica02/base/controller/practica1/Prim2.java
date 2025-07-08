package com.practica02.base.controller.practica1;

import com.practica02.base.domain.controller.dataStruct.list.LinkedList;

public class Prim2 {

    static class Point {
        Integer r;
        Integer c;
        Point parent;

        public Point(int x, int y, Point p) {
            r = x;
            c = y;
            parent = p;
        }

        public Point opposite() {
            if (parent == null) {
                return null;
            }

            if (this.r.compareTo(parent.r) != 0) {

                return new Point(this.r + this.r.compareTo(parent.r), this.c, this);
            }

            if (this.c.compareTo(parent.c) != 0) {

                return new Point(this.r, this.c + this.c.compareTo(parent.c), this);
            }
            return null;
        }
    }

    public char[][] generar(int r, int c) {

        char[][] maz = new char[r][c];
        for (int x = 0; x < r; x++) {
            for (int y = 0; y < c; y++) {
                maz[x][y] = '0';
            }
        }

        Point st = new Point((int) (Math.random() * r), (int) (Math.random() * c), null);
        maz[st.r][st.c] = 'S';

        LinkedList<Prim2.Point> frontier = new LinkedList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {

                if (x == 0 && y == 0 || x != 0 && y != 0) {
                    continue;
                }
                int newR = st.r + x;
                int newC = st.c + y;

                if (newR >= 0 && newR < r && newC >= 0 && newC < c && maz[newR][newC] == '0') {
                    frontier.add(new Point(newR, newC, st));
                }
            }

        }

        Point last = null;
        while (!frontier.isEmpty()) {

            Point cu = frontier.removeRandom();
            // --------------------------------------------------------------

            if (cu == null || cu.parent == null) {
                continue;
            }

            Point op = cu.opposite();

            try {

                if (cu.r >= 0 && cu.r < r && cu.c >= 0 && cu.c < c &&
                        op.r >= 0 && op.r < r && op.c >= 0 && op.c < c) {

                    if (maz[cu.r][cu.c] == '0' && maz[op.r][op.c] == '0') {

                        maz[cu.r][cu.c] = '1';
                        maz[op.r][op.c] = '1';

                        last = op;

                        for (int x = -1; x <= 1; x++) {
                            for (int y = -1; y <= 1; y++) {
                                if (x == 0 && y == 0 || x != 0 && y != 0) {
                                    continue;
                                }
                                int newR = op.r + x;
                                int newC = op.c + y;

                                if (newR >= 0 && newR < r && newC >= 0 && newC < c && maz[newR][newC] == '0') {
                                    frontier.add(new Point(newR, newC, op));
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {

            }
        }

        if (last != null) {
            maz[last.r][last.c] = 'E';
        } else {

            maz[r - 1][c - 1] = 'E';
        }

        return maz;
    }
}