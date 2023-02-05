package com.wsf.huanbaobao.mapper;

import com.wsf.huanbaobao.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressMapperTest {
    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert(){
        Address address = new Address();
        address.setUid(8);
        address.setPhone("1234587524");
        address.setName("test03");
        addressMapper.insert(address);
    }

    @Test
    public void countByUid(){
        addressMapper.countByUid(8);
    }

    @Test
    public void findByUid(){
        List<Address> addresses = addressMapper.findByUid(8);
        System.out.println(addresses);
    }
}
