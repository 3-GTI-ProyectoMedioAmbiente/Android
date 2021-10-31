package com.example.jcherram.appbeacon;


import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

// -----------------------------------------------------------------------------------
// @author: Juan Carlos Hernandez Ramirez
//Fecha: 17/10/2021
// -----------------------------------------------------------------------------------
public class Utilidades {

    public static byte[] stringToBytes ( String texto ) {
        return texto.getBytes();
        // byte[] b = string.getBytes(StandardCharsets.UTF_8); // Ja
    }

    /**
     * Utilidad para pasar de String a UUID
     * @param uuid String de la UUID que queremos transformar
     * @return UUID Resultrado de la transformacion de la String
     */
    public static UUID stringToUUID( String uuid ) {
        if ( uuid.length() != 16 ) {
            throw new Error( "stringUUID: string no tiene 16 caracteres ");
        }
        byte[] comoBytes = uuid.getBytes();

        String masSignificativo = uuid.substring(0, 8);
        String menosSignificativo = uuid.substring(8, 16);
        UUID res = new UUID( Utilidades.bytesToLong( masSignificativo.getBytes() ), Utilidades.bytesToLong( menosSignificativo.getBytes() ) );

        // Log.d( MainActivity.ETIQUETA_LOG, " \n\n***** stringToUUID *** " + uuid  + "=?=" + Utilidades.uuidToString( res ) );

        // UUID res = UUID.nameUUIDFromBytes( comoBytes ); no va como quiero

        return res;
    }

    /**
     * Utilidad para pasar de UUID a String
     * @param uuid Objeto UUID que queremos transformar
     * @return String Resultado de la transformacion proveniente de la string
     */
    public static String uuidToString ( UUID uuid ) {
        return bytesToString( dosLongToBytes( uuid.getMostSignificantBits(), uuid.getLeastSignificantBits() ) );
    }

    /**
     * Utilidad para pasar de UUID a Hexadecimal en String
     * @param uuid Objeto UUID que queremos transformar
     * @return String Resultado en Hexadecimal de la transformacion de UUID
     */
    public static String uuidToHexString ( UUID uuid ) {
        return bytesToHexString( dosLongToBytes( uuid.getMostSignificantBits(), uuid.getLeastSignificantBits() ) );
    }

    /**
     *  Utilidad para pasar de una lista de Bytes a String
     * @param bytes Lista de bytes que queremos transformar
     * @return String Resultado de la transformacion de la lista de Bytes
     */
    public static String bytesToString( byte[] bytes ) {
        if (bytes == null ) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append( (char) b );
        }
        return sb.toString();
    }

    /**
     * Forma una array de bytes, según los 2 numeros de tipo long. Primero se pondra el numero mas significativo y luego el menos
     * @param masSignificativos long mas significada que se alamacenar en la lista de bytes
     * @param menosSignificativos
     * @return byte[]
     */
    public static byte[] dosLongToBytes( long masSignificativos, long menosSignificativos ) {
        ByteBuffer buffer = ByteBuffer.allocate( 2 * Long.BYTES );
        buffer.putLong( masSignificativos );
        buffer.putLong( menosSignificativos );
        return buffer.array();
    }

    /**
     * Utilidad para pasar de una lista de Bytes a un tipo Int
     * @param bytes lista de bytes a transformar
     * @return int resultado de la transformacion
     */
    public static int bytesToInt( byte[] bytes ) {
        return new BigInteger(bytes).intValue();
    }

    /**
     * Utilidad para trasnformar una lista de bytes a un long
     * @param bytes lista de bytes a transformar
     * @return long resultado ded la transformacion
     */
    public static long bytesToLong( byte[] bytes ) {
        return new BigInteger(bytes).longValue();
    }

    /**
     * Compruebas si el byte que recibimos es valido
     * @param bytes lista de bytes a comprobar
     * @return int resultado de la transformacion
     */
    public static int bytesToIntOK( byte[] bytes ) {
        if (bytes == null ) {
            return 0;
        }

        if ( bytes.length > 4 ) {
            throw new Error( "demasiados bytes para pasar a int ");
        }
        int res = 0;

        for( byte b : bytes ) {
           /*
           Log.d( MainActivity.ETIQUETA_LOG, "bytesToInt(): byte: hex=" + Integer.toHexString( b )
                   + " dec=" + b + " bin=" + Integer.toBinaryString( b ) +
                   " hex=" + Byte.toString( b )
           );
           */
            res =  (res << 8) // * 16
                    + (b & 0xFF); // para quedarse con 1 byte (2 cuartetos) de lo que haya en b
        } // for

        if ( (bytes[ 0 ] & 0x8) != 0 ) {
            // si tiene signo negativo (un 1 a la izquierda del primer byte
            res = -(~(byte)res)-1; // complemento a 2 (~) de res pero como byte, -1
        }
       /*
        Log.d( MainActivity.ETIQUETA_LOG, "bytesToInt(): res = " + res + " ~res=" + (res ^ 0xffff)
                + "~res=" + ~((byte) res)
        );
        */

        return res;
    }

    /**
     * Utilidad para pasr de una lista de bytes a un String con formato hexadecimal
     * @param bytes lista de bytes a transformar
     * @return String resultado de la transformacion en formato hexadeciamal
     */
    public static String bytesToHexString( byte[] bytes ) {

        if (bytes == null ) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
            sb.append(':');
        }
        return sb.toString();
    }
}

