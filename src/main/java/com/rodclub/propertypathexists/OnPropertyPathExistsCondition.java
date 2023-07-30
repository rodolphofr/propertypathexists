package com.rodclub.propertypathexists;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OnPropertyPathExistsCondition extends SpringBootCondition {
	private static final String APPLICATION_PROPERTIES = "application";
	private static final String DOT = ".";

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		var propertyPath = (String) metadata
			.getAnnotationAttributes(ConditionalOnPropertyPathExist.class.getName())
			.get("value");
		
		if (propertyPath == null)
			throw new NullPointerException("Property path is null");
			
		var message = ConditionMessage
			.forCondition(ConditionalOnPropertyPathExist.class);
		
		return checkPropertyPathExists(propertyPath, context.getEnvironment())
			? ConditionOutcome.match(message.foundExactly(propertyPath))
			: ConditionOutcome.noMatch(message.didNotFind(propertyPath).atAll());
	}
	
	private boolean checkPropertyPathExists(String propertyPath, Environment env) {
		var configurableEnv = (ConfigurableEnvironment) env;
		var path = propertyPath + DOT;
		
		return configurableEnv.getPropertySources().stream()
			.filter(propSource -> propSource instanceof EnumerablePropertySource)
			.filter(propSource -> propSource.getName().contains(APPLICATION_PROPERTIES))
			.map(propSource -> (EnumerablePropertySource<?>) propSource)
			.map(enumerable -> enumerable.getPropertyNames())
			.flatMap(Arrays::stream)
			.anyMatch(propName -> propName.startsWith(path));
	}
}
