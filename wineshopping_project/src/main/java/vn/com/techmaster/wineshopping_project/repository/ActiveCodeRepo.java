package vn.com.techmaster.wineshopping_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.techmaster.wineshopping_project.model.ActiveCode;

public interface ActiveCodeRepo extends JpaRepository<ActiveCode,String> {
}
