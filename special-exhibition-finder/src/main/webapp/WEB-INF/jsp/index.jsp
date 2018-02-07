<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<!--
	Snapshot by TEMPLATED
	templated.co @templatedco
	Released for free under the Creative Commons Attribution 3.0 license (templated.co/license)
-->
<html>
	<head>
		<title>Special Exhibition Finder</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" type="text/css" href="/css/main.css" />
	</head>
	<body>
		<div class="page-wrap">

			<!-- Nav -->
				<nav id="nav">
					<ul>
						<li><a href="/index" class="active"><span class="icon fa-home"></span></a></li>
						<li><a href="/gallery"><span class="icon fa-camera-retro"></span></a></li>
					</ul>
				</nav>

			<!-- Main -->
				<section id="main">				

					<!-- Banner -->
						<section id="banner">
							<div class="inner">
								<h1>SPECIAL EXHIBITION FINDER</h1>
								<p>진행 중인 박물관 특별 전시를 찾아드립니다.</p>
								<ul class="actions">
									<li><a href="#galleries" class="button alt scrolly big">알아보기</a></li>
								</ul>
							</div>
						</section>

					<!-- Gallery -->
						<section id="galleries">

							<!-- Photo Galleries -->
								<div class="gallery">
									<div class="index_header">
										최근 업데이트 :
										<span>${updated}</span>
									</div>
									<header class="special">
										<h2>진행 중인 전시</h2>
									</header>
									<div class="content">
										<c:forEach items="${exhibitionList}" var="ex">
											<div class="media">
												<a href="/gallery">
													<img src="${ex.image}" alt="" title="${ex.name}" />
													<div class="image_name">
														<b>${ex.name}</b>
													</div>
													<div class="image_museum">
														${ex.museum.name}
														</br>
														${ex.getCalculatedRemainingDays()}일 후 종료
													</div>
												</a>
											</div>
          								</c:forEach>
									</div>
									<footer>
										<a href="/gallery" class="button big">전체 보기</a>
									</footer>
								</div>
						</section>

					<!-- Footer -->
						<footer id="footer">
							<div class="copyright">
								&copy; Untitled Design: <a href="https://templated.co/">TEMPLATED</a>. Images: <a href="https://unsplash.com/">Unsplash</a>.
							</div>
						</footer>
				</section>
		</div>

		<!-- Scripts -->
			<script src="/js/jquery.min.js"></script>
			<script src="/js/jquery.poptrox.min.js"></script>
			<script src="/js/jquery.scrolly.min.js"></script>
			<script src="/js/skel.min.js"></script>
			<script src="/js/util.js"></script>
			<script src="/js/main.js"></script>

	</body>
</html>