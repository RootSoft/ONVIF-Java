package be.teletask.onvif.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomas Verhelst on 05/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class OnvifPacket {

    //Constants
    public static final String TAG = OnvifPacket.class.getSimpleName();

    //Attributes
    private String name;
    private String timestamp;
    private byte[] data;

    //Constructors
    public OnvifPacket() {
        this("", new byte[0]);
    }

    public OnvifPacket(String name) {
        this(name, new byte[0]);
    }

    public OnvifPacket(String name, byte[] data) {
        this.name = name;
        this.data = data;
    }

    //Methods

    //Properties

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public static byte[] asciiToBytes(String ascii) {

        int val1, val2, val3;
        char c1, c2;

        List<Byte> bytes = new ArrayList<>();

        ascii = ascii.replace("\\r", "\\0d");
        ascii = ascii.replace("\\n", "\\0a");

        for (int i = 0; i < ascii.length(); i++) {
            val1 = iAt(ascii, i);
            if (val1 >= 0x20 && val1 <= 0x7E) {

                if (val1 == (((int) '\\') & 0xff)) {
                    val2 = iAt(ascii, i + 1);
                    val3 = iAt(ascii, i + 2);
                    if (val2 > -1 && val3 > -1) {
                        c1 = ascii.charAt(i + 1);
                        c2 = ascii.charAt(i + 2);

                        try {
                            val2 = Integer.parseInt((c1 + "") + (c2 + ""), 16) & 0xff;
                            val3 = 0;

                            bytes.add((byte) (val2 + val3));

                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                        i += 2;
                    }

                } else {
                    val1 = iAt(ascii, i);
                    bytes.add((byte) (val1));

                }
            }
        }

        return toByteArray(bytes);
    }

    private static int iAt(String s, int index) {
        if (index < s.length()) {
            return (((int) s.charAt(index)) & 0xff);
        } else {
            return -1;
        }
    }

    private static byte[] toByteArray(List<Byte> list) {
        byte[] ret = new byte[list.size()];
        for (int i = 0; i < ret.length; i++)
            ret[i] = list.get(i);
        return ret;
    }

    public String toAscii(byte[] data) {
        StringBuilder returnString = new StringBuilder();
        for (int item : data) {
            if (item == 0x0A) {
                returnString.append("\\n");

            } else if (item == 0x0D) {
                returnString.append("\\r");

            } else if (item >= 0x20 && item <= 0x7E) {
                returnString.append((char) item);
            } else {
                String hex = Integer.toHexString(item & 0xff);
                if (hex.length() == 1) {
                    hex = "0" + hex;
                }
                returnString.append("\\").append(hex);
            }
        }

        return returnString.toString();
    }

}
