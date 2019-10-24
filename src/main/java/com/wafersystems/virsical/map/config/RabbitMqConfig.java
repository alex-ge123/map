package com.wafersystems.virsical.map.config;

import com.wafersystems.virsical.common.core.constant.MapMqConstants;
import com.wafersystems.virsical.common.core.constant.UpmsMqConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq配置
 *
 * @author tandk
 * @date 2018/12/5 15:09
 */
@Configuration
public class RabbitMqConfig {

  /**
   * 配置消息转换器转Json
   *
   * @return 消息转换器
   */
  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  /**
   * 地图素材广播交换机
   *
   * @return FanoutExchange
   */
  @Bean
  public FanoutExchange svgFanoutExchange() {
    return new FanoutExchange(MapMqConstants.EXCHANGE_FANOUT_MAP_SVG);
  }

}