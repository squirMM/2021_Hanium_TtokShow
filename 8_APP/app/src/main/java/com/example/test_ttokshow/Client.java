package com.example.test_ttokshow;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
    public class Client{
        public static void main(String... args) {


            try (Socket client = new Socket()) {
                InetSocketAddress ipep = new InetSocketAddress("3.144.33.17", 9999);
                //3.144.33.17
                client.connect(ipep);

                try (OutputStream sender = client.getOutputStream(); InputStream receiver = client.getInputStream()) {

                    for (int i = 0; i < 1; i++) {

                        byte[] data = new byte[4];
                        receiver.read(data, 0, 4);
                        ByteBuffer bu = ByteBuffer.wrap(data);
                        bu.order(ByteOrder.LITTLE_ENDIAN);

                        int length = bu.getInt();
                        data = new byte[length];
                        receiver.read(data, 0, length);

                        String msg = new String(data, "UTF-8");
                        output = msg.split("#");
                        for (i=0; i<output.length;){
                            System.out.println(output[i++]);

                        }/*
                        System.out.println("-----");
                        String[] test = getOutput();
                        System.out.println(test[0]); // 이름
                        System.out.println(test[1]); // 날짜
                        System.out.println(test[2]); // 내용
                        System.out.println(test[3]); // 별점
                        System.out.println(test[4]); // 위치
                        */
                    }

                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        private static String[] output;
        static String[] getOutput(){
            return output;
        }

    }

