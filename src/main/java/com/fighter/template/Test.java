package com.fighter.template;

/**
 * Created by caihaijun@navercorp.com on 2018/6/11.
 */
public class Test {
    public static void main(String[] args) {
        Point a = new Point(1, 1);
        Point b = new Point(5, 3);
        int c = a.area(b);
    }
}
class Point {
    int x, y;
    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int area(Point b) {
        int length = Math.abs(b.y - this.y);
        int width = Math.abs(b.x - this.x);
        return length * width;
    }
}
