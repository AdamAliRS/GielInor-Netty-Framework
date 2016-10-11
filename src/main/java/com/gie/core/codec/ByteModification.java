package com.gie.core.codec;

/**
 * Represent Runescape's custom value types.
 * 
 * @author SeVen
 */
public enum ByteModification {
    
    /**
     * No modifications
     */
    STANDARD,
    
    /**
     * Adds 128 to the value.
     */
    ADDITION,
    
    /**
     * Places a negative sign on the value.
     */
    NEGATION,
    
    /**
     * Subtracts the value from 128.
     */
    SUBTRACTION
    
}
