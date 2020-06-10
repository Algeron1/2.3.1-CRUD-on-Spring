package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDao;
import web.model.Role;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public Role getRoleById(long id) {
        return roleDao.getRoleById(id);
    }
}
