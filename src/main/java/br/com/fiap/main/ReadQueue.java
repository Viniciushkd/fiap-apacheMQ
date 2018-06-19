package br.com.fiap.main;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class ReadQueue {

	public static void main(String[] args) throws Exception {
		CamelContext ctx = new DefaultCamelContext();
			
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://0.0.0.0:61616"); // 0.0.0.0 acessar a propria maquina, somente a porta e exposta
		ctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		
		ctx.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("jms:fiap31scj").to("stream:out").to("file:/home/ibm/Exercicios/outputFolder/");
			}
		});
		
		ctx.start();
		Thread.sleep(5 * 60 * 1000);
		ctx.stop();
		
	}
}
