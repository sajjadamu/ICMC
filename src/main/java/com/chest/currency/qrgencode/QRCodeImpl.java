package com.chest.currency.qrgencode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chest.currency.entity.model.AuditorProcess;
import com.chest.currency.entity.model.BankReceipt;
import com.chest.currency.entity.model.BranchReceipt;
import com.chest.currency.entity.model.DSB;
import com.chest.currency.entity.model.DiversionIRV;
import com.chest.currency.entity.model.FreshFromRBI;
import com.chest.currency.entity.model.Process;
import com.chest.currency.entity.model.ProcessBundleForCRAPayment;
import com.chest.currency.entity.model.SoiledRemittanceAllocation;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeImpl implements QRCodeGen {

	private static final Logger LOG = LoggerFactory.getLogger(QRCodeImpl.class);
	private String filePath;

	public String getPath(String userId) {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int date = now.get(Calendar.DATE);

		File file = new File(filePath);

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH-mm-ss-S");
		Date imageName = new Date();
		String filePathLocal = filePath + "/" + year + "/" + month + "/" + date + "/";
		LOG.debug("FILE PATH==" + filePathLocal);
		file = new File(filePathLocal);
		file.setReadable(true, false);
		file.setExecutable(true, false);
		file.setWritable(true, false);
		if (!file.exists()) {
			if (file.mkdirs()) {
				LOG.debug("Directory is created!");
			} else {
				LOG.debug("Failed to create directory!");
			}
		}
		filePathLocal += userId + "-" + dateFormat.format(imageName) + ".png";
		LOG.debug("FILE PATH1==" + filePathLocal);
		return filePathLocal;
	}

	public String generateQR(BranchReceipt branchReceipt) {

		String filePath = getPath(branchReceipt.getInsertBy());

		String qrCodeData = "";

		qrCodeData = "sol Id : " + branchReceipt.getSolId() + "\n" + " Branch : " + branchReceipt.getBranch() + "\n"
				+ " Denomination : " + branchReceipt.getDenomination() + "\n" + " Bundle : " + branchReceipt.getBundle()
				+ "\n" + " Total : " + branchReceipt.getTotal() + "\n" + " Bin :" + branchReceipt.getBin();

		String charset = "UTF-8"; // or "ISO-8859-1"
		Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		try {
			createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.debug("QR Code image created successfully!");

		try {
			LOG.debug("Data read from QR Code: " + readQRCode(filePath, charset, hintMap));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePath;
	}

	public String generateDSBQR(DSB dsb) {
		LOG.debug("QR Code Dsb data" + dsb);
		String filePath = getPath(dsb.getInsertBy());
		String qrCodeData = "";
		qrCodeData = "Denomination  " + dsb.getDenomination() + "\n" + "Bundle : " + dsb.getBundle() + "\n" + "Total : "
				+ dsb.getTotal() + "\n" + "Bin :" + dsb.getBin();
		String charset = "UTF-8"; // or "ISO-8859-1"
		Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		try {
			createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOG.debug("QR Code image created successfully!");

		try {
			LOG.debug("Data read from QR Code: " + readQRCode(filePath, charset, hintMap));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePath;
	}

	public String generateDirvQR(DiversionIRV dirv) {

		String filePath = getPath(dirv.getInsertBy());

		String qrCodeData = "";

		qrCodeData = "Denomination : " + dirv.getDenomination() + "\n" + "Bundle : " + dirv.getBundle() + "\n"
				+ "Total : " + dirv.getTotal() + "\n" + "Bin :" + dirv.getBinNumber();

		String charset = "UTF-8"; // or "ISO-8859-1"
		Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		try {
			createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOG.debug("QR Code image created successfully!");

		try {
			LOG.debug("Data read from QR Code: " + readQRCode(filePath, charset, hintMap));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePath;
	}

	public String generateFreshFromRBIQR(FreshFromRBI freshFromRBI) {

		String filePath = getPath(freshFromRBI.getInsertBy());

		String qrCodeData = "";

		qrCodeData = "Denomination : " + freshFromRBI.getDenomination() + "\n" + "Bundle : " + freshFromRBI.getBundle()
				+ "\n" + "Total : " + freshFromRBI.getTotal() + "\n" + "Bin :" + freshFromRBI.getBin() + "\n"
				+ "No. Of Bags :" + freshFromRBI.getNoOfBags();

		String charset = "UTF-8"; // or "ISO-8859-1"
		Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		try {
			createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOG.debug("QR Code image created successfully!");

		try {
			LOG.debug("Data read from QR Code: " + readQRCode(filePath, charset, hintMap));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePath;
	}

	public String generateProcessingRoomQR(Process process) {
		String filePath = getPath(process.getInsertBy());
		String qrCodeData = "";
		qrCodeData = process.getCurrencyType() + "|" + process.getDenomination() + "|" + process.getBinNumber() + "|"
				+ process.getMachineNo();
		String charset = "UTF-8"; // or "ISO-8859-1"
		Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		try {
			createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOG.debug("QR Code image created successfully!");

		try {
			LOG.debug("Data read from QR Code: " + readQRCode(filePath, charset, hintMap));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePath;
	}

	@SuppressWarnings("unchecked")
	public static void createQRCode(String qrCodeData, String filePath, String charset,
			@SuppressWarnings("rawtypes") Map hintMap, int qrCodeheight, int qrCodewidth)
			throws WriterException, IOException {
		try {
			LOG.info("createQRCode try");
			LOG.info("filePath " + filePath);
			LOG.info("hintMap " + hintMap);

			BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
					BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);

			LOG.info("filePath.substring(filePath.lastIndexOf('.') "
					+ filePath.substring(filePath.lastIndexOf('.') + 1));

			MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1),
					new File(filePath));

		} catch (Exception e) {
			LOG.info("createQRCode catch " + e);
			e.printStackTrace();
		} 
	}

	@SuppressWarnings("unchecked")
	public static String readQRCode(String filePath, String charset, @SuppressWarnings("rawtypes") Map hintMap)
			throws FileNotFoundException, IOException, NotFoundException {
		BinaryBitmap binaryBitmap = new BinaryBitmap(
				new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(filePath)))));
		Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, hintMap);
		return qrCodeResult.getText();
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String generateICMCReceiptQR(BankReceipt icmcReceipt) {

		String filePath = getPath(icmcReceipt.getInsertBy());

		String qrCodeData = "";

		qrCodeData = "Denomination : " + icmcReceipt.getDenomination() + "\n" + "Bundle : " + icmcReceipt.getBundle()
				+ "\n" + "Total : " + icmcReceipt.getTotal() + "\n" + "Bin :" + icmcReceipt.getBinNumber();

		String charset = "UTF-8"; // or "ISO-8859-1"
		Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		try {
			createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		LOG.debug("QR Code image created successfully!");

		try {
			LOG.debug("Data read from QR Code: " + readQRCode(filePath, charset, hintMap));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return filePath;
	}

	@Override
	public String generateCRAPaymentProcessingRoomQR(ProcessBundleForCRAPayment processBundleForCRAPayment) {
		String filePath = getPath(processBundleForCRAPayment.getInsertBy());
		String qrCodeData = "";
		qrCodeData = processBundleForCRAPayment.getCurrencyType() + "|" + processBundleForCRAPayment.getDenomination();
		String charset = "UTF-8"; // or "ISO-8859-1"
		Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		try {
			createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		LOG.debug("QR Code image created successfully!");

		try {
			LOG.debug("Data read from QR Code: " + readQRCode(filePath, charset, hintMap));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return filePath;
	}

	@Override
	public String generateQRForSoiled(SoiledRemittanceAllocation soiled) {

		String filePath = getPath(soiled.getInsertBy());

		String qrCodeData = "";

		qrCodeData = " Denomination : " + soiled.getDenomination() + "\n" + " Bundle : " + soiled.getRequestBundle();

		String charset = "UTF-8"; // or "ISO-8859-1"
		Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		try {
			createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		LOG.debug("QR Code image created successfully!");

		try {
			LOG.debug("Data read from QR Code: " + readQRCode(filePath, charset, hintMap));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return filePath;
	}

	@Override
	public String generateProcessingRoomQRAuditor(AuditorProcess process) {
		String filePath = getPath(process.getInsertBy());
		String qrCodeData = "";
		qrCodeData = process.getCurrencyType() + "|" + process.getDenomination() + "|" + process.getBinNumber();
		String charset = "UTF-8"; // or "ISO-8859-1"
		Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		try {
			createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		LOG.debug("QR Code image created successfully!");

		try {
			LOG.debug("Data read from QR Code: " + readQRCode(filePath, charset, hintMap));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return filePath;
	}

}