/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.clienttoclient.messaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.System.out;

@SpringBootApplication
public class WSC2CApplication {

    public static void main(String[] args) {

        verifyCommandLineArgs(args);

        SpringApplication.run(WSC2CApplication.class, args);

    }

    static void verifyCommandLineArgs(String[] args) {

        out.println("\n");
        for (int i = 0; i < args.length; i++)
            out.println(args[i]);
        out.println("\n");


        switch (args.length) {
            case 2:
                if (args[0].equals("-port"))
                    validateForPort(args[1]);
                else if (args[0].equals("-configpath"))
                    validateForPath(args[1]);
                else
                    usage();
                break;

            case 4:
                if (args[0].equals("-port")) {
                    validateForPort(args[1]);
                    if (!args[2].equals("-configpath"))
                        usage();
                    validateForPath(args[3]);
                } else if (args[0].equals("-configpath")) {
                    validateForPath(args[1]);
                    if (!args[2].equals("-port"))
                        usage();
                    validateForPort(args[3]);
                } else {
                    usage();
                }
                break;

            default:
                usage();
        }
    }

    private static void usage() {
        out.println("incorrent command line arguments.  usage:\n");
        out.print("[-port nnnn [default 8080]] ");
        out.println("[-configpath \"path-to-config-property-file\"]");
        System.exit(1);

    }

    private static void validateForPort(String port) {

        try {
            int portNumber = Integer.valueOf(port);
            return;
        } catch(NumberFormatException | NullPointerException e) {
            out.println("port number is invalid.");
            System.exit(1);
        }

    }

    private static void validateForPath(String path) {

        try {

            if (Files.isDirectory(Paths.get(path)))
                return;

        } catch(FileSystemNotFoundException e) {

        }
        out.println("config file path is invalid.");
        System.exit(1);

    }

}