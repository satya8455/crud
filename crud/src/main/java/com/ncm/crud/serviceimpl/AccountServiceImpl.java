package com.ncm.crud.serviceimpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncm.crud.dto.UserVo;
import com.ncm.crud.entity.Account;
import com.ncm.crud.repository.AccountRapo;
import com.ncm.crud.service.AccountService;
@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRapo accountrapo;
	
	@Override
	public Account save1(UserVo user) {
		Account usercreate=new  Account();
		try {
			
			BeanUtils.copyProperties(user,usercreate);
			
			usercreate.setFirstname(user.getName());
			 accountrapo.save(usercreate);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return usercreate;
	}

	 public boolean authenticate(Account user) {
	        if (user == null || user.getEmail() == null || user.getPassword() == null) {
	            return false;
	        }

	        try {
	            Account existingUser = accountrapo.findByEmail(user.getEmail());
	            if (existingUser == null) {
	                return false;
	            }
	            if(existingUser.getPassword().equals(user.getPassword()))
	            		{
	            	return true;
	            		}
	            
	        //    return existingUser.getPassword().equals(user.getPassword());
	        } catch (Exception e) {
	            // Handle the exception
	            e.printStackTrace();
	            return false;
	        }
			return false;
	    }
}
