package vn.com.techmaster.wineshopping_project.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.techmaster.wineshopping_project.model.ActiveCode;
import vn.com.techmaster.wineshopping_project.repository.ActiveCodeRepo;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class ActiveCodeService {
    private ActiveCodeRepo activeCodeRepo;
    public ActiveCode addCode(String regisCode, String user_id){
        String id = UUID.randomUUID().toString();
        ActiveCode newActive = ActiveCode.builder()
                .id(id)
                .code(regisCode)
                .user_id(user_id)
                .build();
        activeCodeRepo.save(newActive);
        return newActive;
    }
    public ConcurrentHashMap<String, String> getAllActiveCode() {
        ConcurrentHashMap<String, String> results = new ConcurrentHashMap<>();
        List<ActiveCode> activecodes = activeCodeRepo.findAll();
        for (int i = 0; i < activecodes.size(); i++) {
            results.put(activecodes.get(i).getCode(),activecodes.get(i).getUser_id());
        }
        return results;
    }
}
