package com.touchspring.smartforecasting.controller.sys;

import com.touchspring.smartforecasting.domain.dto.MESUser;
import com.touchspring.smartforecasting.repository.sys.SysLogRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
public class MockController {


    private final SysLogRepository sysLogRepository;

    public MockController(SysLogRepository sysLogRepository) {
        this.sysLogRepository = sysLogRepository;
    }

    /**
     * 分页查询信息
     *
     * @return .
     */
    @GetMapping("/mock/users")
    public Map<String, Object> mockUserApi() {
        MESUser mockUser = new MESUser();
        mockUser.setId("2");
        mockUser.setName("admin2");
        ArrayList<MESUser> mockUsers = new ArrayList<>();
        mockUsers.add(mockUser);
        HashMap<String, Object> data = new HashMap<>();
        data.put("code", 200);
        data.put("data", mockUsers);
        return data;
    }

}
