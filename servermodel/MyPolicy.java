package servermodel;

import java.io.FilePermission;
import java.net.SocketPermission;
import java.security.*;
import java.util.PropertyPermission;

public class MyPolicy extends Policy {

    private static PermissionCollection perms;

    public MyPolicy() {
        super();
        if (perms == null) {
            perms = new MyPermissionCollection();
            addPermissions();
        }
    }

    @Override
    public PermissionCollection getPermissions(CodeSource codesource) {
        return perms;
    }

    private void addPermissions() {
        SocketPermission socketPermission = new SocketPermission("*:1024-", "connect, resolve");
        PropertyPermission propertyPermission = new PropertyPermission("*", "read, write");
        FilePermission filePermission = new FilePermission("<<ALL FILES>>", "read, write, execute, delete, readlink");

        perms.add(new AllPermission());
        perms.add(socketPermission);
        perms.add(propertyPermission);
        perms.add(filePermission);
    }

}