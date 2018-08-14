package com.bridgelabz.userservice.utilservice.modelmapperservice;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.userservice.utilservice.exceptions.RestPreconditions;
import com.bridgelabz.userservice.utilservice.exceptions.ToDoExceptions;


/**<p><b>Mapping model service class to map DTO To Model and vise versa</b></p>
 * @author yuga
 * @since 06/08/2018
 *
 */
@Component
public class ModelMapperService {
	@Autowired
	ModelMapper modelMapper;
	
	
	/**
	 * @param source
	 * @param destinationType
	 * <p><b>To map the properties of model to dto and dto to model.</b></p>
	 * @return destination object
	 * @throws ToDoExceptions
	 */
	public <D> D map(Object source, Class<D> destinationType) throws ToDoExceptions {
		RestPreconditions.checkNotNull(source, "NULLPointerException : source cant not be null");
		RestPreconditions.checkNotNull(destinationType, "NULLPointerException : Model class name can not be null");
		return modelMapper.map(source, destinationType);
	}


}
