package com.hyrax.backend.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.hyrax.backend.exception.ErrorType;
import com.hyrax.backend.exception.HyraxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MarkService {

    enum MarkEnum {

        SHANGHAI_DA_ZHONG("上海大众", "shanghai_da_zhong.jpg"),
        BAO_MA("宝马", "bao_ma.jpg"),
        AO_DI("奥迪", "ao_di.jpg"),
        BEN_CHI("奔驰", "ben_chi.jpg"),
        FU_TE("福特", "fu_te.jpg"),
        NI_SANG("尼桑", "ni_sang.jpg"),
        BEIJING_XIAN_DAI("北京现代", "beijing_xian_dai.jpg");

        private final String brand;
        private final String icon;

        MarkEnum(String brand, String icon) {
            this.brand = brand;
            this.icon = icon;
        }

        public String getBrand() {
            return brand;
        }

        public String getIcon() {
            return icon;
        }

    }

    private static final Logger log = LoggerFactory.getLogger(VehicleService.class);
    private static final String MARK_RESOURCE_PATH = "mark/";
    private static final String MARK_RESOURCE_URL = "/vehicle/mark";

    private static final Map<String, String> markMap = new HashMap<>();

    @Value("${hyrax.server.address}")
    private String serverAddress;

    public MarkService() {
        MarkEnum[] marks = MarkEnum.values();
        for (MarkEnum markEnum : marks) {
            markMap.put(markEnum.getBrand(), markEnum.getIcon());
        }
    }

    public String getUrl(String brand) {
        String icon = markMap.get(brand);
        if (icon == null || "".equals(icon)) {
            throw new HyraxException(ErrorType.VEHICLE_BRAND_NOT_EXISTS);
        }

        String markUrl = serverAddress + MARK_RESOURCE_URL + "/" + icon + "/noToken";
        return markUrl;
    }

    /**
     * 获取车标图片
     * @param icon 图片名称
     * @return
     */
    public byte[] getMark(String icon) {
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            BufferedImage bufferedImage = ImageIO.read(classLoader.getResource(MARK_RESOURCE_PATH + icon));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", bos);

            return bos.toByteArray();
        } catch (IOException | IllegalArgumentException e) {
            log.warn("get vehicle mark failed ", e);
            throw new HyraxException(ErrorType.RESOURCE_NOT_FOUND);
        }
    }
}
