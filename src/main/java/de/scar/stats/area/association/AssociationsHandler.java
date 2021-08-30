package de.scar.stats.area.association;

import java.util.HashMap;

public class AssociationsHandler {

    private static HashMap<Long, Association> associations = new HashMap<>();

    public static boolean isUserAssociated(Long userId) {
        return associations.containsKey(userId);
    }

    public static Association getAssociation(Long userId) {
        return associations.get(userId);
    }

    public static void associate(Long userId, String name) {
        associations.put(userId, new Association(name));
    }

}