/*
 * Copyright (C) 2014 Universidad de Alicante
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.cervantesvirtual.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Creates a new file and a backup copy of the previous version if the file
 * already exists
 *
 * @author RCC
 */
public class SafeFile extends File {

    private static final long serialVersionUID = 1L;

    private static String backupName(File file) {
        StringBuilder backname = new StringBuilder();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmm");
        String version = format.format(new Date(file.lastModified()));
        String name;
        try {
            name = file.getCanonicalPath();
        } catch (IOException e) {
            name = file.getName();
        }
        //CanonicalPath();
        int lastdot = name.lastIndexOf('.');

        backname.append(name.substring(0, lastdot));
        backname.append("-v").append(version);
        backname.append(name.substring(lastdot, name.length()));
        return backname.toString();
    }

    public static void backup(String filename) {
        File file;
        FileChannel source;
        FileChannel backup;
        try {
            file = new File(filename);
            if (file.exists()) {
                String backname = backupName(file);
                System.out.println(backname);
                source = new FileInputStream(file).getChannel();
                backup = new FileOutputStream(backname).getChannel();
                backup.transferFrom(source, 0, source.size());
            }
        } catch (java.io.IOException e) {
            System.err.println("Could not create backup for file " + filename);
        }
    }

    public void backup() {
        backup(getName());
    }

    public SafeFile(String filename) {
        super(filename);
        if (exists()) {
            backup(getName());
        }
    }
}
