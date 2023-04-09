/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.brickbreaker;


import java.awt.BasicStroke;  // used for outliniing
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Admin
 */
public class MapGenerator {
    public int map[][];        // 2d-array to reperesent bricks
    public int bricksWidth;
    public int bricksHeight;
    public MapGenerator(int row , int col){
        map = new int[row][col];
        for (int[] map1 : map) {          // initializing values of map with 1
             for (int j = 0; j < map[0].length; j++) {
                 map1[j] = 1;
             }
         }
        bricksWidth = 540/col;      // width of each brick  (container size / no of bricks)
        bricksHeight = 150/row;     // Height of each brick
    }
    public void draw(Graphics2D g) {   // displaying bricks on screen acc. to map array
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    g.setColor(Color.red);
                    g.fillRect(j * bricksWidth + 80, i * bricksHeight + 50, bricksWidth, bricksHeight); //red box

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * bricksWidth + 80, i * bricksHeight + 50, bricksWidth, bricksHeight); // black lines on red box

                }
            }

        }
    }
    public void setBricksValue(int value,int row,int col)
    {
        map[row][col] = value;

    }
    
}