package portalconnector.model;

public enum Permission {
    UNAUTHORIZED(0),
    AUTHORIZED(1),
    ADMIN(2),
    SUPERUSER(3);

    int permissionLevel;

    Permission(int permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }
}
