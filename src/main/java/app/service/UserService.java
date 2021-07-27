package app.service;



import app.entity.Role;
import app.entity.User;
import app.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    public User findByUsername (String username){
        return userRepo.findByUsername(username);
    }
    public List<User> findAll () {return userRepo.findAll();}
    public void deleteById (Long id) {userRepo.deleteById(id);}
    public void addUser (User user){userRepo.save(user);}
    public User findById(Long id){return userRepo.findById(id).get();}
    public void save(User user){userRepo.save(user);}
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("User '%s not found", username ));
        }
        // Приводим нашего юзера к ЮзерДетэйлзу, последний параметр коллекция прав доступа
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }
    // из коллекции ролей получает коллекцию прав доступа
    private Collection <? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        // берет пачку ролей и делает пачку Ауторитез с точно такими же строками
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
