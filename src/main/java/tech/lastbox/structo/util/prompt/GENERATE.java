package tech.lastbox.structo.util.prompt;

public enum GENERATE
{
    STRUCTURE {
        @Override
        public String generate(String description, String history, String baseInfo) {
            return ("You are a software architect assistant. Given a project description, including the tech stack, required entities and classes, and general application functionality, generate only the project structure in JSON format. " +
                    "Do not include any explanatory messages before or after the JSON output. " +
                    description +
                    "The output should be a JSON representation of the project structure, including folders for components, controllers, models, etc., with the class names specified within each package. For instance, a 'controllers' package might look like: 'controllers': ['UserController', 'ProductController', 'OrderController']." +
                    "Do not include any additional text or explanations.").replaceAll("[\\r\\n]", " ");
        }

   }, TASK {
        @Override
        public String generate(String description, String history, String baseInfo) {
            return ("You are a software architect assistant. Given a project description and its structure, generate a list of tasks in JSON format. Do not include any explanatory messages before or after the JSON output." +
                    "Project Description:" +
                    description +
                    "The output should be a JSON array of tasks. Each task should have a 'Name', 'Description' and 'ProjectPath'.  'ProjectPath' should indicate where the task should be implemented in the project structure. Be specific and provide context in the descriptions, using relevant keywords related to governmental audits.  " +
                    "For instance: [{'Name': 'GENERATE Risk Matrix', 'Description': 'Develop a functionality to automatically generate a risk matrix based on audit objectives and using a Generative AI model trained on governmental audit data and risk analysis.', 'ProjectPath': 'src/main/java/com/example/auditplatform/riskmanagement'}, {'Name': 'Analyze Audit Recommendations', 'Description': 'Utilize a Generative AI model to analyze the fulfillment of audit recommendations based on the provided documents and generate a report summarizing the findings.', 'ProjectPath': 'src/main/java/com/example/auditplatform/recommendationanalysis'}]. " +
                    "Do not include any additional text or explanations.").replaceAll("[\\r\\n]", " ");
        }
    }, DIAGRAM {
        @Override
        public String generate(String description, String history, String baseInfo) {
            return ("You are a software architect assistant. Given a project description and its structure, generate a list of diagrams in JSON format. Each diagram should be represented in Mermaid syntax. Do not include any explanatory messages before or after the JSON output." +
                    "Project Description: " + description +
                    "The output should be a JSON array of diagrams. Each diagram should have a 'Name', 'Type', 'Description', 'Mermaid Code' and 'ProjectPath'.  'Type' can be  'UseCase Diagram', 'Class Diagram', 'Sequence Diagram', or 'Data Flow Diagram'. 'ProjectPath' should indicate where the diagram should be implemented in the project structure. 'Mermaid Code' should contain the Mermaid code for the diagram. Be specific and provide context in the descriptions, using relevant keywords related to governmental audits." +
                    "For instance: [{'Name': 'Risk Management', 'Type': 'UseCase Diagram', 'Description': 'A use case diagram illustrating the interactions between auditors and the system to manage and analyze audit risks.', 'Mermaid Code': '```mermaid\ngraph LR\nAuditor --> (Manage Risks)\nAuditor --> (Analyze Risks)\n(Manage Risks) --> System\n(Analyze Risks) --> System\n```', 'ProjectPath': 'documentation/diagrams/risks'}, {'Name': 'Audit Recommendation Analysis', 'Type': 'Sequence Diagram', 'Description': 'A sequence diagram detailing the process of analyzing audit recommendations using a Generative AI model, including the data input, processing, and report generation steps.', 'Mermaid Code': '```mermaid\nsequenceDiagram\nAuditor->>+AI Model: Input Audit Data\nAI Model->>-Auditor: GENERATE Analysis Report\n```', 'ProjectPath': 'documentation/diagrams/recommendations'}]. " +
                    "Do not include any additional text or explanations.").replaceAll("[\\r\\n]", " ");
        }
    }, USERMESSAGE {
        @Override
        public String generate(String currentMessage, String history, String baseInfo) {
            return ("You are an expert software engineer. Given a user message and the conversation history, provide a response that solves the user's problem in a JSON format, considering the conversation context. " +
                    "\n\nBase informations about the project:\n\n```\n" + baseInfo + "\n```\n\n" +
                    "\n\nConversation History:\n\n```\n" + history  + "\n```\n\n" +
                    "User Message:\n\n" + "```\n \n" + currentMessage + "```\n\n" +
                    "Follow these instructions to formulate your answer:\n\n" +
                    "* Analyze the conversation history to understand the user's problem. \n" +
                    "* Utilize prompt engineering techniques to formulate an accurate and relevant response.\n" +
                    "* If necessary, ask the user for more information to clarify the problem.\n" +
                    "* Focus on providing practical and helpful solutions based on your expertise in governmental audits. " +
                    "Do not include any additional text or explanations.").replaceAll("[\\r\\n]", " ");
        }
    };

    protected abstract String generate(String description, String history, String baseInfo);

    public String data(String description) {
        return generate(description, "", "");
    }

    public String data(String currentMessage, String history, String baseInfo) {
        return generate(currentMessage, history, baseInfo);
    }
}
