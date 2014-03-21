package br.com.badrequest.insporte.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import br.com.badrequest.insporte.model.Image;

@Stateful
public class ImageService {

	@Inject
	EntityManager em;

	public Image getImage(Long id) {
		
		try {
			Image image = (Image) em
					.createQuery("SELECT im from Image im where im.id = :id")
					.setParameter("id", id).getSingleResult();
			
			return image;
		} catch (NoResultException noResultException) {
			return null;
		}
		
	}
	
	public Image updateImage(Long id, String imageBase64) {
		Image image = getImage(id);
		
		String filePath = String.format("/home/insporte/images/%d.png", id);

//		String filePath = "/tmp/"+id.toString()+".png";

		try {
			File file = new File(filePath);
			OutputStream os = new FileOutputStream(file);
			os.write(Base64.decodeBase64(imageBase64));
			os.close();
		} catch (Exception e) {
			return null;
		}

		image.setPath(filePath);
		
		return em.merge(image);
	}
	
	public String getImageBase64(Long id) {
		byte[] binaryData = getImageBytes(id);
		if (binaryData != null) {
			return Base64.encodeBase64String(binaryData);
		}
		return null;
	}
	
	public byte[] getImageBytes(Long id) {
		Image image = getImage(id);
		try {
			InputStream is = new FileInputStream(image.getPath());
			return IOUtils.toByteArray(is);
		} catch (Exception e) {
			return null;
		}
	}
 }
