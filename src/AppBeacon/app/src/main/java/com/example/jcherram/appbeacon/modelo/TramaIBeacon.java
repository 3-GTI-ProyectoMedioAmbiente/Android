
package com.example.jcherram.appbeacon.modelo;

import java.util.Arrays;

// -----------------------------------------------------------------------------------
// @author: Juan Carlos Hernandez Ramirez
// fecha;12/11/21
// TramalBeacon
// clase para recoger los beacons
// -----------------------------------------------------------------------------------
public class TramaIBeacon {
    private byte[] prefijo = null; // 9 bytes
    private byte[] uuid = null; // 16 bytes
    private byte[] major = null; // 2 bytes
    private byte[] minor = null; // 2 bytes
    private byte txPower = 0; // 1 byte

    private byte[] losBytes;

    private byte[] advFlags = null; // 3 bytes
    private byte[] advHeader = null; // 2 bytes
    private byte[] companyID = new byte[2]; // 2 bytes
    private byte iBeaconType = 0 ; // 1 byte
    private byte iBeaconLength = 0 ; // 1 byte

    /**
     * Constructor de la trama IBeacon.
     * @param bytes lista de bytes desde la que se creara el objeto IBeacon
     */
    public TramaIBeacon(byte[] bytes ) {
        this.losBytes = bytes;

        prefijo = Arrays.copyOfRange(losBytes, 0, 8+1 ); // 9 bytes
        uuid = Arrays.copyOfRange(losBytes, 9, 24+1 ); // 16 bytes
        major = Arrays.copyOfRange(losBytes, 25, 26+1 ); // 2 bytes
        minor = Arrays.copyOfRange(losBytes, 27, 28+1 ); // 2 bytes
        txPower = losBytes[ 29 ]; // 1 byte

        advFlags = Arrays.copyOfRange( prefijo, 0, 2+1 ); // 3 bytes
        advHeader = Arrays.copyOfRange( prefijo, 3, 4+1 ); // 2 bytes
        companyID = Arrays.copyOfRange( prefijo, 5, 6+1 ); // 2 bytes
        iBeaconType = prefijo[ 7 ]; // 1 byte
        iBeaconLength = prefijo[ 8 ]; // 1 byte

    }

    /**
     * Getter de la clase IBeacon que devuelve el atributo Prefijo de la clase IBeacon
     * @return byte[] lista de bytes resultante
     */
    public byte[] getPrefijo() {
        return prefijo;
    }

    /**
     * Getter de la clase IBeacon que devuelve el atributo UUID de la clase IBeacon
     * @return byte[] lista de bytes resultante
     */
    public byte[] getUUID() {
        return uuid;
    }

    /**
     * Getter de la clase IBeacon que devuelve el atributo Major de la clase IBeacon
     * @return byte[] lista de bytes resultante
     */
    public byte[] getMajor() {
        return major;
    }


    /**
     * Getter de la clase IBeacon que devuelve el atributo Minor de la clase IBeacon
     * @return byte[] lista de bytes resultante
     */
    public byte[] getMinor() {
        return minor;
    }


    /**
     * Getter de la clase IBeacon que devuelve el atributo TxPowe de la clase IBeacon
     * @return byte byte que representa el atributo txPowe
     */
    public byte getTxPower() {
        return txPower;
    }


    /**
     * Getter de la clase IBeacon que devuelve el atributo losBytes de la clase IBeacon
     * @return byte[] lista de bytes resultante
     */
    public byte[] getLosBytes() {
        return losBytes;
    }


    /**
     * Getter de la clase IBeacon que devuelve el atributo advFlags de la clase IBeacon
     * @return byte[] lista de bytes resultante
     */
    public byte[] getAdvFlags() {
        return advFlags;
    }


    /**
     * Getter de la clase IBeacon que devuelve el atributo advHeader de la clase IBeacon
     * @return byte[] lista de bytes resultante
     */
    public byte[] getAdvHeader() {
        return advHeader;
    }


    /**
     * Getter de la clase IBeacon que devuelve el atributo CompanyID de la clase IBeacon
     * @return byte[] lista de bytes resultante
     */
    public byte[] getCompanyID() {
        return companyID;
    }


    /**
     * Getter de la clase IBeacon que devuelve el atributo IBeaconType de la clase IBeacon
     * @return byte[] lista de bytes resultante
     */
    public byte getiBeaconType() {
        return iBeaconType;
    }


    /**
     * Getter de la clase IBeacon que devuelve el atributo IBeaconLenght de la clase IBeacon
     * @return byte[] lista de bytes resultante
     */
    public byte getiBeaconLength() {
        return iBeaconLength;
    }

}

