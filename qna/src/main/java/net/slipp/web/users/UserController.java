package net.slipp.web.users;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.slipp.dao.users.UserDAO;
import net.slipp.domain.users.Authenticate;
import net.slipp.domain.users.User;

@Controller
@RequestMapping("/users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserDAO userDao;
	
	@RequestMapping("/form")
	public String createForm(Model model){
		model.addAttribute("user", new User());
		return "users/form";
	}

	@RequestMapping(value="", method=RequestMethod.POST)
	public String create(@Valid User user, BindingResult bindingResult){
		logger.debug("User : {}" , user);
		if(bindingResult.hasErrors()){
			logger.debug("Binding Result has error!");
			List<ObjectError> errors = bindingResult.getAllErrors();
			for(ObjectError error : errors){
				logger.debug("error : {}, {}", error.getObjectName(), error.getDefaultMessage());
			}
			return "users/form";
		}
		
		userDao.create(user);
		logger.debug("Database : {}", userDao.findById(user.getUserId()));
		return "redirect:/";
	}
	
	@RequestMapping("{userId}/form")
	public String updateForm(@PathVariable String userId, Model model){
		if(userId == null){
			throw new IllegalArgumentException("����� ���̵� �ʿ��մϴ�.");
		}
		
		User user = userDao.findById(userId);
		model.addAttribute("user", user);
		return "users/form";
	}
	
	@RequestMapping(value="", method=RequestMethod.PUT)
	public String update(@Valid User user, BindingResult bindingResult, HttpSession session){
		logger.debug("User : {}" , user);
		if(bindingResult.hasErrors()){
			logger.debug("Binding Result has error!");
			List<ObjectError> errors = bindingResult.getAllErrors();
			for(ObjectError error : errors){
				logger.debug("error : {}, {}", error.getObjectName(), error.getDefaultMessage());
			}
			return "users/form";
		}
		
		Object temp = session.getAttribute("userId");
		if(temp == null){
			throw new NullPointerException();
		}
		
		userDao.update(user);
		logger.debug("Database : {}", userDao.findById(user.getUserId()));
		return "redirect:/";
	}
	
		
	@RequestMapping("/login/form")
	public String loginForm(Model model){
		System.out.println("aaaa");
		model.addAttribute("authenticate", new Authenticate());
		return "users/login";
	}
	@RequestMapping("/login")
	public String login(@Valid Authenticate authenticate, BindingResult bindingResult, HttpSession session, Model model){

		if(bindingResult.hasErrors()){
			return "users/login";
		}
		
		User user = userDao.findById(authenticate.getUserId());
		if(user == null){
			System.out.println(user);
			model.addAttribute("errorMessage","�������� �ʴ� ������Դϴ�.");
			return "users/login";
		}
		
		if(!user.getPassword().equals(authenticate.getPassword())){
			//���� ó�� - ��й�ȣ�� ���� ���� ��
			model.addAttribute("errorMessage","��й�ȣ�� Ʋ���ϴ�.");
			return "users/login";
		}
		
		session.setAttribute("userId", user.getUserId());
		
		//���ǿ� ����� ���� ����
		
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.removeAttribute("userId");
		return "redirect:/";
		
	}
}
