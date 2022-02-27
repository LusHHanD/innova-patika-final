//package com.innova.project.patikafinal.utils.modelmapper;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ModelMapperHandler<E, D> {
//
//    private ModelMapper modelMapper;
//
//    private final Class<D> typeOfD;
//    private final Class<E> typeOfE;
//
//    public ModelMapperHandler(ModelMapper modelMapper, Class<E> typeOfE, Class<D> typeOfD) {
//        this.modelMapper = modelMapper;
//        this.typeOfE = typeOfE;
//        this.typeOfD = typeOfD;
//    }
//
//    public E toEntity(D dto) {
//        return modelMapper.map(dto, typeOfE);
//    }
//
//    public D toDto(E entity) {
//        return modelMapper.map(entity, typeOfD);
//    }
//}
