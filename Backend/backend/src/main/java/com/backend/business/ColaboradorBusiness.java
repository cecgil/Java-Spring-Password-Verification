package com.backend.business;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;

import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;

import com.backend.dto.ChefeSubordinado;
import com.backend.entity.ColaboradorEntity;
import com.backend.repository.ColaboradorRepository;

@Service
public class ColaboradorBusiness {

	@Autowired
	ColaboradorRepository colaboradorRepository;

	private static final String PASSWORD = "senha123";
	private static final String SALT = "salt123";
	private static final int KEY_LENGTH = 128;
	
	public ColaboradorEntity findById (Integer id) {
		return colaboradorRepository.findById(id).get();
	}

	public List<ColaboradorEntity> findAll() {
		return colaboradorRepository.findAll();

	}

	public ColaboradorEntity save(ColaboradorEntity colaboradorEntity) throws Exception {
		
        String complexidade = calcularComplexidadeSenha(colaboradorEntity.getSenha());

		
		colaboradorEntity.setScore(complexidade);
		
		colaboradorEntity.setSenha(encryptPassword(colaboradorEntity.getSenha()));
		
		return colaboradorRepository.save(colaboradorEntity);
	}
	
	 public static String calcularComplexidadeSenha(String senha) {
	        int comprimento = senha.length();
	        boolean temLetrasMaiusculas = false;
	        boolean temLetrasMinusculas = false;
	        boolean temDigitos = false;
	        boolean temCaracteresEspeciais = false;

	        for (char c : senha.toCharArray()) {
	            if (Character.isUpperCase(c)) {
	                temLetrasMaiusculas = true;
	            } else if (Character.isLowerCase(c)) {
	                temLetrasMinusculas = true;
	            } else if (Character.isDigit(c)) {
	                temDigitos = true;
	            } else {
	                temCaracteresEspeciais = true;
	            }
	        }

	        int complexidade = 0;

	        // Adicione pontuações com base nos critérios
	        if (comprimento >= 8) {
	            complexidade += 2;
	        } else if (comprimento >= 6) {
	            complexidade += 1;
	        }

	        if (temLetrasMaiusculas) {
	            complexidade += 2;
	        }

	        if (temLetrasMinusculas) {
	            complexidade += 2;
	        }

	        if (temDigitos) {
	            complexidade += 2;
	        }

	        if (temCaracteresEspeciais) {
	            complexidade += 3;
	        }

	        // Determine a força com base na complexidade
	        if (complexidade <= 5) {
	            return "Muito Fraca";
	        } else if (complexidade <= 8) {
	            return "Fraca";
	        } else if (complexidade <= 12) {
	            return "Média";
	        } else if (complexidade <= 16) {
	            return "Forte";
	        } else {
	            return "Muito Forte";
	        }
	    }
	 
	 public static String encryptPassword(String password) {
		 try {
			 SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			 KeySpec spec = new PBEKeySpec(PASSWORD.toCharArray(), SALT.getBytes(), 1000, KEY_LENGTH);
			 SecretKeySpec secretKeySpec = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
			 
			 Cipher cipher = Cipher.getInstance("AES");
			 cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			 
			 byte[] encryptedBytes = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
			 return Base64.getEncoder().encodeToString(encryptedBytes);
		 } catch (Exception e) {
			 e.printStackTrace();
			 return null;
			 
		 }
		 
	 }
	 
	public String delete(Integer id) {
		colaboradorRepository.deleteById(id);
		return "deleted" + id;
	}

	public ColaboradorEntity associaSubordinado(ChefeSubordinado chefeSubordinadoDTO) {
		
		Optional<ColaboradorEntity> chefe = colaboradorRepository.findById(chefeSubordinadoDTO.getIdChefe());
		Optional<ColaboradorEntity> subordinado = colaboradorRepository.findById(chefeSubordinadoDTO.getIdSubordinado());
		
		subordinado.setChefe(chefe);
		

	}

	}


