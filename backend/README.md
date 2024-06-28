这是一个基于Spring Boot、Redis、RabbitMQ、MySQL、MyBatis-Plus的高炉数值智能分析平台
本项目旨在通过一个先进的数据分析平台，实现高炉数据的自动化处理与智能分析。用户仅需导入高炉数据集并明确其分析需求，系统便能通过AIGC自动生成相应的可视化图表及深入分析结论，如高炉的运行状态等，从而有效降低数据分析成本，提高工作效率。此外，项目整合了讯飞星火大模型，该大模型基于大量高炉数据预先训练，能够提供精准的高炉数据分析。平台还支持调用深度学习模型，不仅可以生成高炉的最新数据，还能进行即时的状态诊断，实现对高炉运行的全面智能分析与控制。这一整合解决方案，为高炉的运营管理提供了强大的数据支持和智能决策工具。

技术栈：

前端:
1. React
2. 开发框架 Umi + Ant Design Pro
3. 可视化开发库(Echarts + HighCharts + AntV)
4. umi openapi 代码生成(自动生成后端调用代码)

后端:
1. Spring Boot 
2. MySQL数据库
3. MyBatis Plus数据访问框架
4. 消息队列(RabbitMQ)
5. AI 能力(讯飞星火大模型)
6. Excel 的上传和数据的解析(Easy Excel)  
7. Swagger + Knife4j 项目接文档   
8. Hutool 工具库

   项目预览
   ![image](https://github.com/NHZB2000/-/assets/96872947/f50e48be-05a0-4acb-b8b7-9278cec5e683)
![image](https://github.com/NHZB2000/-/assets/96872947/5fdee31c-025d-42bc-930f-4074721f37eb)

