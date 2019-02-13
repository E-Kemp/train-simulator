/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Platform class that represents a platform with multiple points in it that are adjacent
 * Are usually parts of a path algorithm
 * @author Elliot Jordan Kemp
 */
public class Platform extends Point {

    protected final String PLATFORM_NUM;
    
    public Platform(String code, String PtNum) {
        super(code);
        this.PLATFORM_NUM = PtNum;
    }
    
}
