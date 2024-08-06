package v.yeikovych.endtoendtest.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BlacklistService {

    private static final List<UUID> BLACKLIST = new ArrayList<>();

    public boolean isBlacklistedId(UUID id) {
        return BLACKLIST.contains(id);
    }

}
